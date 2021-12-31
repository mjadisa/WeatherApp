package com.mujeeb.weatherapp.ui



import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.common.BackPressAware
import com.mujeeb.weatherapp.common.CITY_KEY
import com.mujeeb.weatherapp.common.CustomDialog
import com.mujeeb.weatherapp.data.city_list.Result
import com.mujeeb.weatherapp.databinding.FragmentCityListBinding
import com.mujeeb.weatherapp.viewmodel.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.content_city_list.*
import javax.inject.Inject
@AndroidEntryPoint
class CityListFragment : Fragment(), SelectCityInterface, BackPressAware {

    private val cityListViewModel: CityListViewModel by viewModels()

    @Inject lateinit var cityListRecyclerViewAdapter: CityListRecyclerViewAdapter

    private lateinit var binding: FragmentCityListBinding



    private var activityCallback: OnClickCallback? = null

    private var cityToDisplay: String? = null




    companion object {
        fun newInstance(): CityListFragment {
            return CityListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            cityToDisplay = bundle.getString(CITY_KEY)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            activityCallback = context as OnClickCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + getString(R.string.callback))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(this.context)
        rv_city_list.layoutManager = linearLayoutManager
        rv_city_list.addItemDecoration(
            DividerItemDecoration(
                this.context,
                linearLayoutManager.orientation
            )
        )
       rv_city_list.adapter = cityListRecyclerViewAdapter

        binding.progressVisibility = cityListViewModel.getProgressObservable()


        cityToDisplay?.let { cityListViewModel.searchCity(it) }


        cityListViewModel.getCityObservable().observe(this, Observer {
            it?.let {
                when (it) {
                    is CityListRequestState.LocationFoundSuccess -> it.cityResponse.list?.let { cityListRecyclerViewAdapter.updateList(it) }
                    is CityListRequestState.NoNetworkConnectionError -> showNoNetworkErrorMsg()
                    is CityListRequestState.SomethingWentWrongError -> showSomethingWentWrongErrorMsg()

                }
            }

        })

    }


    private fun showSomethingWentWrongErrorMsg(){
        CustomDialog.showSomethingWentWrongDialog(activity!!) {}
    }
    private fun showNoNetworkErrorMsg() {
        CustomDialog.showNoNetworkConnectionDialog(activity!!) {}
    }
    override fun onItemSelected(cityList: Result) {
        this.activityCallback?.onClickCallback(cityList)

    }

    interface OnClickCallback {
        fun onClickCallback(cityList: Result)

    }

    override fun onDestroyView() {
       rv_city_list?.adapter = null
        super.onDestroyView()

    }

    override fun onBackPressed(): Boolean {
        return true
    }


}
