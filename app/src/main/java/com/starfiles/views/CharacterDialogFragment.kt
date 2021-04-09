package com.starfiles.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.starfiles.R
import com.starfiles.data.models.Character
import com.starfiles.databinding.CharacterFragmentBinding

class CharacterDialogFragment(val character: Character) :DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CharacterFragmentBinding = DataBindingUtil.inflate<CharacterFragmentBinding>(layoutInflater,
            R.layout.character_fragment, null, false)
        binding.character = character
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window
            ?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }
}