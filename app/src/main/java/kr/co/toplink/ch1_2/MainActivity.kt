package kr.co.toplink.ch1_2

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.toplink.ch1_2.adapter.SingleViewBinderListAdapter
import kr.co.toplink.ch1_2.databinding.ActivityMainBinding
import kr.co.toplink.ch1_2.databinding.ItemPostBinding
import kr.co.toplink.ch1_2.model.Post
import kr.co.toplink.ch1_2.model.PostCardModel
import kr.co.toplink.ch1_2.viewholder.ItemBinder
import kr.co.toplink.ch1_2.viewholder.PostCardViewBinder
import java.util.Random
import kotlin.collections.ArrayList

const val KEY_POST_MODEL = "post-card-model"

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var listAdapter : SingleViewBinderListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Ch1-2 리사이클뷰 에니메이션 변형"

        val postCardViewBinder = PostCardViewBinder {
            binding, postCardModel ->
            gotoDetailWithTransition(postCardModel, binding)
        }

        listAdapter = SingleViewBinderListAdapter(postCardViewBinder as ItemBinder)

        binding.recyclerView.apply {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

         listAdapter.submitList(generateMockPosts())
        //setContentView(R.layout.activity_main)
    }

    /*
        액티비티 에니메이션 변형 바로가기 목록 함수
     */
    private fun gotoDetailWithTransition(
        postCardModel: PostCardModel,
        binding: ItemPostBinding
    ) {
        val intent = Intent(this@MainActivity, Activity1_2Details::class.java)

        intent.putExtra(KEY_POST_MODEL, postCardModel)

        // create the transition animation using image, title and body
        val pairIvAvatar = Pair<View, String>(binding.ivPhoto, binding.ivPhoto.transitionName)
        val pairTvTitle = Pair<View, String>(binding.tvTitle, binding.tvTitle.transitionName)
        val pairTvBody = Pair<View, String>(binding.tvBody, binding.tvBody.transitionName)

        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
//                pairTvTitle,
                pairIvAvatar
            )

        // start the new activity
        startActivity(intent, options.toBundle())
    }

    private fun generateMockPosts() : List<PostCardModel> {
        val postList = ArrayList<PostCardModel>()
        val random = Random()

        repeat(30) {
            val randomNum = random.nextInt(5)
            val title = "제목 $randomNum"
            val postBody = getString(R.string.bacon_ipsum)
            val post = Post(it, it, title, postBody)
            postList.add(PostCardModel(post, getDrawableRes(randomNum)))
        }

        return postList
    }

    private fun getDrawableRes(userId: Int): Int {
        return when {
            userId % 6 == 0 -> {
                R.drawable.avatar_1_raster
            }
            userId % 6 == 1 -> {
                R.drawable.avatar_2_raster
            }
            userId % 6 == 2 -> {
                R.drawable.avatar_3_raster
            }
            userId % 6 == 3 -> {
                R.drawable.avatar_4_raster
            }
            userId % 6 == 4 -> {
                R.drawable.avatar_5_raster
            }
            else -> {
                R.drawable.avatar_6_raster
            }
        }
    }

}