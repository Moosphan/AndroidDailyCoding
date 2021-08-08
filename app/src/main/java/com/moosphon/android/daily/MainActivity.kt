package com.moosphon.android.daily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.moosphon.android.cellpacking.library.ChannelReader
import com.moosphon.android.daily.databinding.ActivityMainBinding
import com.moosphon.android.daily.extension.viewBinding
import com.moosphon.android.daily.track.pages.TrackSampleListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // 通过属性委托绑定viewBinding对象
    private val mainBinding by viewBinding(ActivityMainBinding::bind)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolBar()
        //initViewBinding()
        mainBinding.trackButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, TrackSampleListActivity::class.java))
        }
    }

    /**
     * 默认方式获取viewBinding对象
     */
    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initToolBar() {
        toolbarMain.title = getString(R.string.app_name) + ChannelReader.getChannelFromMetadata(this)
    }
}