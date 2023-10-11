package com.example.sololearntesttask

import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sololearntesttask.main.GitHubRepositoryViewModel
import com.example.sololearntesttask.models.DetailsOwnerDTO
import com.example.sololearntesttask.models.GitReposFullDTO
import com.example.sololearntesttask.models.OwnerDTO
import com.example.sololearntesttask.models.RepositoryDTO
import com.example.sololearntesttask.models.RepositoryDetailsDTO
import com.example.sololearntesttask.network.GitReposInteractor


class GitHubRepositoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var interactor: GitReposInteractor

    @Mock
    private lateinit var reposListObserver: Observer<List<GitReposFullDTO>>

    @Mock
    private lateinit var errorObserver: Observer<String>

    private lateinit var viewModel: GitHubRepositoryViewModel

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = GitHubRepositoryViewModel(interactor)
        viewModel.reposListLiveData.observeForever(reposListObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        viewModel.reposListLiveData.removeObserver(reposListObserver)
        viewModel.errorLiveData.removeObserver(errorObserver)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getSortedRepoWithDetails should update reposListLiveData on success`() = runBlocking {
        // Arrange
        val repo1 = GitReposFullDTO(
            RepositoryDTO(
                "repo1", "user1/repo1", "some description 1",
                OwnerDTO("", "user1")
            ),
            RepositoryDetailsDTO(
                DetailsOwnerDTO(""),
                5000,
                23,
                "repo1",
                "some description 1",
                "Kotlin",
                ""
            )
        )
        val repo2 = GitReposFullDTO(
            RepositoryDTO(
                "repo2", "user2/repo2", "some description 2",
                OwnerDTO("", "user2")
            ),
            RepositoryDetailsDTO(
                DetailsOwnerDTO(""),
                2000,
                12,
                "repo2",
                "some description 2",
                "Java",
                ""
            )
        )
        val repo3 = GitReposFullDTO(
            RepositoryDTO(
                "repo3", "user3/repo3", "some description 3",
                OwnerDTO("", "user3")
            ),
            RepositoryDetailsDTO(
                DetailsOwnerDTO(""),
                4000,
                56,
                "repo3",
                "some description 3",
                "Python",
                ""
            )
        )
        val testData = listOf(repo1, repo2, repo3)
        `when`(interactor.getRepositoriesWithDetails()).thenReturn(testData)

        // Act
        viewModel.getSortedRepoWithDetails()

        // Assert
        verify(interactor).getRepositoriesWithDetails()
        verify(reposListObserver).onChanged(testData.sortedByDescending { it.gitRepoDetailDTO.stargazersCount })
        verifyNoMoreInteractions(interactor, reposListObserver)
    }

    @Test
    fun `getSortedRepoWithDetails should update errorLiveData on failure`() = runBlocking {
        // Arrange
        val errorMessage = "An error occurred"
        `when`(interactor.getRepositoriesWithDetails()).thenThrow(RuntimeException(errorMessage))

        // Act
        viewModel.getSortedRepoWithDetails()

        // Assert
        verify(interactor).getRepositoriesWithDetails()
        verify(errorObserver).onChanged(errorMessage)
        verifyNoMoreInteractions(interactor, errorObserver)
    }
}