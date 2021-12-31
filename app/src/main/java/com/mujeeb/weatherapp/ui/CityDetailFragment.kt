package com.mujeeb.weatherapp.ui



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.common.RESULT_KEY
import com.mujeeb.weatherapp.data.city_detail.Response
import com.mujeeb.weatherapp.databinding.FragmentWeatherListBinding
import com.mujeeb.weatherapp.viewmodel.CityDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_weather_list.*


import javax.inject.Inject

@AndroidEntryPoint
class CityDetailFragment : Fragment() {

    private val cityDetailViewModel: CityDetailViewModel by viewModels()

    @Inject
    lateinit var cityDetailRecyclerViewAdapter: CityDetailRecyclerViewAdapter


    private lateinit var binding: FragmentWeatherListBinding

    private var cityDetailToDisplay: Int? = null


    companion object {
        fun newInstance(): CityDetailFragment {
            return CityDetailFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            cityDetailToDisplay = bundle.getInt(RESULT_KEY)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(this.context)
        rv_weather_list.layoutManager = linearLayoutManager
        rv_weather_list.addItemDecoration(
            DividerItemDecoration(
                this.context,
                linearLayoutManager.orientation
            )
        )
        rv_weather_list.adapter = cityDetailRecyclerViewAdapter

        binding.progressVisibility = cityDetailViewModel.getProgressObservable()

        cityDetailToDisplay?.let { cityDetailViewModel.getWeatherData(it) }


        cityDetailViewModel.getWeatherObservable().observe(this, Observer {
            it?.let {
                when (it) {
                    is CityDetailRequestState.Success -> {
                        it.response.list?.let { cityDetailRecyclerViewAdapter.updateList(it) }
                        showResponse(it.response)
                    }
                    is CityDetailRequestState.Error -> Toast.makeText(activity, it.error, Toast.LENGTH_LONG).show()

                }
            }
        })


    }


    private fun showResponse(response: Response) {
        tv_location.text = String.format("%s %s"  , response.city?.name, response.city?.country)


    }
}