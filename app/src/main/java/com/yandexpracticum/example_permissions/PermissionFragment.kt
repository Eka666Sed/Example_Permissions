package com.yandexpracticum.example_permissions

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yandexpracticum.example_permissions.databinding.FragmentPermissionBinding
import android.Manifest


class PermissionFragment : Fragment() {
    private var binding: FragmentPermissionBinding? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Пользователь дал разрешение, можно продолжать работу
                binding?.permissionRequestFrame?.visibility = View.GONE
                binding?.permissionGranted?.visibility = View.VISIBLE
            } else {
                // Пользователь отказал в предоставлении разрешения
                binding?.permissionRequestFrame?.visibility = View.VISIBLE
                binding?.permissionGranted?.visibility = View.GONE
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        binding?.permissionRequestFrame?.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun checkPermission() {
        val permissionProvided = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        )
        if (permissionProvided == PackageManager.PERMISSION_GRANTED) {
            binding?.permissionRequestFrame?.visibility = View.GONE
            binding?.permissionGranted?.visibility = View.VISIBLE
        } else if (permissionProvided == PackageManager.PERMISSION_DENIED) {
            binding?.permissionRequestFrame?.visibility = View.VISIBLE
            binding?.permissionGranted?.visibility = View.GONE
        }
    }
}