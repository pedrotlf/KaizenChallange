package com.example.kaizenchallange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kaizenchallange.presentation.SportsList
import com.example.kaizenchallange.presentation.SportsViewModel
import com.example.kaizenchallange.presentation.theme.KaizenChallangeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaizenChallangeTheme {
                val viewModel = viewModel<SportsViewModel> {
                    SportsViewModel(
                        repository = MyApplication.appModule.kaizenRepository
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    SportsList(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}