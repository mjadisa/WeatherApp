package com.mujeeb.weatherapp.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.common.DateUtils
import com.mujeeb.weatherapp.common.Direction
import com.mujeeb.weatherapp.common.WindDirectionUtils
import com.mujeeb.weatherapp.data.city_list.Result
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CityListRecyclerViewAdapter(private val listener: SelectCityInterface, @ApplicationContext val context: Context, private val picasso: Picasso) :
    RecyclerView.Adapter<CityListRecyclerViewAdapter.ViewHolder>() {

    private var mValues: List<Result>? = null

    private val dateUtils: DateUtils = DateUtils()

    private val windDirectionUtils: WindDirectionUtils = WindDirectionUtils()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_city_item, parent, false)
        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = mValues?.get(position)

        holder.location.text =  String.format("%s %s", currentCity?.name,  currentCity?.sys?.country)

        holder.itemView.setOnClickListener {
            if (currentCity != null) {
                listener.onItemSelected(currentCity)
            }
        }

        holder.temperature.text =  String.format("%s %s",context.getString(R.string.current_temperature),  currentCity?.main?.temp)

        holder.windSpeed.text =  String.format("%s %s",context.getString(R.string.wind_speed),  currentCity?.wind?.speed)

        holder.info.text =  currentCity?.weather?.get(0)?.description

        currentCity?.dt?.let { holder.timeOfMeasurement.text = dateUtils.toTimeStampFromDate(it.toLong())}


        var direction: Direction? = null


        currentCity?.wind?.deg?.let {direction = windDirectionUtils.estimateDirection(it) }


        val imageUrl = getImageUrl(direction)


        val imageView = holder.windDirectionImage


       if (imageUrl != 0) {
            picasso.load(imageUrl)
                .into(imageView)
        }


    }

    override fun getItemCount(): Int {

        return mValues?.size ?: 0

    }

    fun updateList(cities: List<Result>) {
        this.mValues = cities
        notifyDataSetChanged()
    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var temperature: TextView = itemView.findViewById(R.id.tv_weather_temperature)

        var windSpeed: TextView = itemView.findViewById(R.id.tv_speed_wind)

        var info: TextView = itemView.findViewById(R.id.tv_weather_info)

        var timeOfMeasurement : TextView = itemView.findViewById(R.id.tv_weather_time)

        var windDirectionImage: ImageView = itemView.findViewById(R.id.iv_direction_wind)

        var location: TextView = itemView.findViewById(R.id.tv_location_name)

    }


    private fun getImageUrl(direction: Direction?) : Int{

        return when (direction) {
            Direction.North -> R.drawable.icons8_north_direction_48
            Direction.East -> R.drawable.icons8_east_direction_48
            Direction.South -> R.drawable.icons8_south_direction_48
            else -> R.drawable.icons8_west_direction_48
        }
    }

}


