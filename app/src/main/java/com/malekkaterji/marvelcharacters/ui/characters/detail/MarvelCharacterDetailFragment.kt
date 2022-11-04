package com.malekkaterji.marvelcharacters.ui.characters.detail

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.malekkaterji.marvelcharacters.databinding.FragmentCharacterDetailBinding
import com.malekkaterji.marvelcharacters.models.MarvelCharacter

class MarvelCharacterDetailFragment : Fragment() {

    private var marvelCharacter: MarvelCharacter? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentCharacterDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_CHARACTER)) {
                marvelCharacter = it.getParcelable(ARG_CHARACTER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.itemDetail

        return rootView
    }

    companion object {
        const val ARG_CHARACTER = "character"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}