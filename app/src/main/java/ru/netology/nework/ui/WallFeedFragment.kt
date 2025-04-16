package ru.netology.nework.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.nework.R
import ru.netology.nework.adapter.WallAdapter
import ru.netology.nework.adapter.WallOnInteractionListener
import ru.netology.nework.databinding.FragmentFeedPostBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.utils.StringArg
import ru.netology.nework.viewmodel.UserViewModel
import ru.netology.nework.viewmodel.WallViewModel
import ru.netology.nmedia.auth.AppAuth
import javax.inject.Inject


@AndroidEntryPoint
class WallFeedFragment : Fragment() {

    @Inject
    lateinit var appAuth: AppAuth
    private val viewModel: WallViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    companion object {
        var Bundle.textArg: String? by StringArg
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding = FragmentFeedPostBinding.inflate(inflater, container, false)


        val authorId = arguments?.textArg?.toInt()
        binding.bottomNavigation.isGone = true
        binding.fab.isGone = appAuth.authState.value?.id != authorId
//        binding.appbar.isVisible = true
//        val user = userViewModel.chooseUser(authorId)
//
//        if (user != null) {
//            binding.avatar.loadAvatar(user.avatar.toString())
//        }
//        binding.collapsingToolbar.title = user?.name
//        tabLayout = binding.tabs
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.wall)), 0, true)
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.job)), 1, false)


        val adapter = WallAdapter(object : WallOnInteractionListener {
            override fun onEdit(post: Post, position: Int) {
            }

            override fun onRemove(post: Post, position: Int) {
            }

            override fun onPlayAudio(post: Post, position: Int) {
                viewModel.playAudio(post)
            }

            override fun onStopAudio() {
                viewModel.clearPlayAudio()
            }

            override fun onLike(post: Post, position: Int) {
                if (authorId != null) {
                    viewModel.likeById(authorId, post)
                }
            }

            override fun onItemClick(post: Post, position: Int) {

            }

            override fun onVideoPlay(position: Int) {
            }

        })
        viewModel.clearPlayAudio()
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest(adapter::submitData)

            }
        }

        viewModel.refreshAdapter.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                appAuth.authState.collectLatest {
                    viewModel.daoClearAll()
                    adapter.refresh()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { state ->
                    binding.swiperefresh.isRefreshing =
                        state.refresh is LoadState.Loading
                }
            }
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.clearPlayAudio()
            adapter.refresh()

        }
        viewModel.onLikeError.observe(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.error)
                .setMessage(R.string.error_like)
                .setPositiveButton(R.string.ok, null)
                .show()
        }

        viewModel.requestSignIn.observe(viewLifecycleOwner) {
            requestSignIn()
        }
        return binding.root
    }

    private fun requestSignIn() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.requestSignInTitle)
            .setMessage(R.string.requestSignInMessage)
            .setPositiveButton(R.string.ok) {
                    _, _,
                ->
                findNavController().navigate(R.id.signInFragment)
            }
            .setNegativeButton(R.string.return_to_posts, null)
            .show()
    }

}
