package ru.netology.nework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentFeedPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel


@AndroidEntryPoint
class PostFeedFragment : Fragment() {

//    @Inject
//    lateinit var appAuth: AppAuth
    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFeedPostBinding.inflate(inflater, container, false)
        binding.bottomNavigation.selectedItemId = R.id.posts
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.posts -> {
                    true
                }

                R.id.events -> {
                    findNavController().navigate(R.id.eventFeedFragment)
                    viewModel.getPosts()
                    true
                }

                else -> false
            }
        }


//        binding.fab.setOnClickListener {
//            if (appAuth.authState.value?.id == 0L) {
//                requestSignIn()
//            } else {
//                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                adapter.loadStateFlow.collectLatest { state ->
//                    binding.swiperefresh.isRefreshing =
//                        state.refresh is LoadState.Loading
//                }
//            }
//        }
//        binding.swiperefresh.setOnRefreshListener {
//            adapter.refresh()
//        }
//        viewModel.onLikeError.observe(viewLifecycleOwner) { id ->
//            MaterialAlertDialogBuilder(requireContext())
//                .setTitle(R.string.error)
//                .setMessage(R.string.error_like)
//                .setPositiveButton(R.string.ok, null)
//                .show()
//        }
//        viewModel.requestSignIn.observe(viewLifecycleOwner) {
//            requestSignIn()
//        }

        return binding.root
    }

//    fun requestSignIn() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(R.string.requestSignInTitle)
//            .setMessage(R.string.requestSignInMessage)
//            .setPositiveButton(R.string.ok) {
//                    _, _,
//                ->
//                findNavController().navigate(R.id.signInFragment)
//            }
//            .setNegativeButton(R.string.return_to_posts, null)
//            .show()
//    }
}
