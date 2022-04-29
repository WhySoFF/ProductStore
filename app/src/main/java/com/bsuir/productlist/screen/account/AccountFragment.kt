package com.bsuir.productlist.screen.account

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bsuir.productlist.R
import com.bsuir.productlist.databinding.FragmentAccountBinding
import com.bsuir.productlist.preferences.AccountPreferences
import com.bsuir.productlist.screen.BaseFragment
import java.io.File

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    companion object {
        private const val USER_IMAGE_NAME = "userImage"
    }

    private val binding: FragmentAccountBinding by viewBinding()

    private val accountPreferences: AccountPreferences by lazy {
        AccountPreferences(requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE))
    }

    private val pickImages =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { userImageUri ->
                binding.userImage.setImageURI(uri)
                saveBitmapToInternalStorage(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        binding.chooseImageButton.setOnClickListener {
            pickImages.launch("image/*")
        }

        loadUserImage()
        loadCredentials()
    }

    override fun onStop() {
        super.onStop()
        accountPreferences.userName = binding.nameField.text.toString()
        accountPreferences.userEmail = binding.emailField.text.toString()
    }

    private fun loadUserImage() {
        if (userHasSavedImage()) {
            binding.userImage.setImageBitmap(getUserImageBitmap())
        } else {
            binding.userImage.setImageResource(R.drawable.ic_question)
        }
    }

    private fun loadCredentials() {
        binding.nameField.setText(accountPreferences.userName)
        binding.emailField.setText(accountPreferences.userEmail)
    }

    private fun saveBitmapToInternalStorage(uri: Uri) {
        val descriptor = requireActivity()
            .contentResolver
            .openFileDescriptor(uri, "r")
            ?.fileDescriptor

        val bitmap = BitmapFactory.decodeFileDescriptor(descriptor)

        requireContext().openFileOutput(USER_IMAGE_NAME, Context.MODE_PRIVATE).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
    }

    private fun getUserImageBitmap(): Bitmap {
        val inputStream = requireContext().openFileInput(USER_IMAGE_NAME)?.buffered()
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun userHasSavedImage() = File(requireContext().filesDir, USER_IMAGE_NAME).exists()

}
