package com.mujeeb.weatherapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.common.BackPressAware
import com.mujeeb.weatherapp.common.CITY_KEY
import com.mujeeb.weatherapp.common.RESULT_KEY
import com.mujeeb.weatherapp.data.city_list.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CityListFragment.OnClickCallback {

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu?.findItem(R.id.action_search)

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }


        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.queryHint = resources.getString(R.string.search_hint)
        searchView?.isQueryRefinementEnabled = true

        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {

                if(newText.length > 2) {
                    showSearchResult(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {


                showSearchResult(query)
                return true
            }
        }

        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onClickCallback(cityList: Result) {

        val toast = Toast.makeText(
            applicationContext,
            String.format("%s %s %s", cityList.name , cityList.sys?.country,  getString(R.string.is_clicked)),
            Toast.LENGTH_SHORT
        )

        toast.show()


        val cityDetailFragment = CityDetailFragment.newInstance()
        val bundle = Bundle()
        cityList.id?.let { bundle.putInt(RESULT_KEY, it) }
        cityDetailFragment.arguments = bundle

        stackView.push(cityDetailFragment)

    }



    fun showSearchResult(city: String){


        val cityListFragment = CityListFragment.newInstance()

        val bundle = Bundle()
        bundle.putString(CITY_KEY, city)
        cityListFragment.arguments = bundle


        stackView.defaultFrag = {
            cityListFragment
        }
        stackView.tag = "chooser_top"
        stackView.fragmentManager = supportFragmentManager
        stackView.reset()



    }


    override fun onBackPressed() {
        val activeFragment = stackView.activeFragment
        if (activeFragment is BackPressAware && activeFragment.onBackPressed()) {
            //do nothing
        } else if (stackView.canPop()) {
            stackView.pop()
        } else {
            super.onBackPressed()
        }
    }

}
