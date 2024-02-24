package com.yandexpracticum.example_permissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.markodevcic.peko.PermissionRequester
import com.yandexpracticum.example_permissions.databinding.FragmentPermissionBinding
import kotlinx.coroutines.launch
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.markodevcic.peko.PermissionResult

class PermissionFragment : Fragment() {
    private lateinit var binding: FragmentPermissionBinding
    val requester = PermissionRequester.instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.permissionRequestFrame.setOnClickListener {
            lifecycleScope.launch {
                requester.request(Manifest.permission.CAMERA).collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            binding.permissionRequestFrame.visibility = View.GONE
                            binding.permissionGranted.visibility = View.VISIBLE
                        }
                        is PermissionResult.Denied.DeniedPermanently -> {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.data= Uri.fromParts("package", context?.packageName, null)
                            context?.startActivity(intent)
                        }
                        is PermissionResult.Denied.NeedsRationale -> {
                            Toast.makeText(requireContext(), "Разрешение на использование геолокации необходимо для доступа к Bluetooth-устройствам", Toast.LENGTH_LONG).show()
                            binding.permissionRequestFrame.visibility = View.VISIBLE
                            binding.permissionGranted.visibility = View.GONE
                        }
                        is PermissionResult.Cancelled -> {
                            return@collect
                        }
                    }
                }
            }
        }
    }
}