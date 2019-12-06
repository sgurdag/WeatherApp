package com.sgurdag.weather_detail.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.safagurdag.core.base.lifecycle.BaseDaggerFragment
import com.safagurdag.core.extensions.observe
import com.safagurdag.repository.interactors.base.PersistenceDataExpired
import com.safagurdag.repository.interactors.base.PersistenceEmpty
import com.safagurdag.repository.interactors.base.State
import com.sgurdag.weather_detail.R
import com.sgurdag.weather_detail.databinding.WeatherDetailBinding
import java.text.SimpleDateFormat
import java.util.*


class WeatherDetailFragment : BaseDaggerFragment<WeatherDetailBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<WeatherDetailViewModel> { viewModelFactory }

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = viewModel
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        binding.fab.setOnClickListener {
            fetchLocation()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fetchLocation()
        observeDataChanges()
    }

    private fun observeDataChanges() {

        // Observing state to show loading
        observe(viewModel.stateData) {

            progress(it is State.Loading)

        }

        // Observing error to show toast with retry action
        observe(viewModel.errorData) {

            if (it is PersistenceEmpty || it is PersistenceDataExpired) {
                Toast.makeText(context, it.messageRes, Toast.LENGTH_LONG).show()
            } else
                showSnackbarWithAction(it) {
                    fetchLocation()
                }
        }

        observe(viewModel.successData) {
            binding.tvLastUiUpdate.text =
                SimpleDateFormat("HH:mm:ss  dd/MMM/yyyy").format(Calendar.getInstance().timeInMillis)
        }

        observe(viewModel.location) {

            if (viewModel.location.value != null)
                viewModel.getWeatherData()
            else
                showSnackbarWithMessageAndAction("Location couldn't be fetched", "Retry") {
                    fetchLocation()
                }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_weather_detail

    override fun onRefresh() {
        fetchLocation()
    }

    private fun progress(status: Boolean) {

        binding.swipeRefreshLayout.isRefreshing = status

        if (status)
            binding.fab.hide()
        else
            binding.fab.show()
    }


    //region location related functions

    private fun fetchLocation() {

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            listenLastKnownLocation()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    //Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> // Permission granted
                    listenLastKnownLocation()
                else -> {
                    // Permission denied.
                    showSnackbarWithMessageAndAction("Location permission is needed", "Retry") {
                        fetchLocation()
                    }
                }
            }
        }
    }

    private fun listenLastKnownLocation() {
        this.let {

            if (checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        REQUEST_PERMISSIONS_REQUEST_CODE
                    )
                } else {

                }


            } else {
                progress(true)
                fusedLocationProviderClient?.lastLocation
                    ?.addOnSuccessListener { lastKnownLocation: Location? ->
                        lastKnownLocation?.let {

                            initWithLocation(it)
                            progress(false)

                        } ?: run {

                            progress(false)
                            showSnackbarWithMessageAndAction(
                                "Location couldn't be fetched",
                                "Settings"
                            ) {
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            }
                        }
                    }
            }
        }

    }

    private fun initWithLocation(location: Location) {
        viewModel.location.value = location
    }

    private fun checkPermissions(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {

        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    // endregion


}
