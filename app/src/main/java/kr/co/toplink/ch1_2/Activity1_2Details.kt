package kr.co.toplink.ch1_2

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import kr.co.toplink.ch1_2.databinding.Activity12detailsBinding
import kr.co.toplink.ch1_2.model.PostCardModel

class Activity1_2Details : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: Activity12detailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity12detailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "카드뷰 변형 상세보기"

        postponeEnterTransition()

        val postCardModel = intent.extras?.getParcelable<PostCardModel>(KEY_POST_MODEL)

        postCardModel?.let {
            binding.ivPhoto.setImageResource(it.drawablesRes)
            binding.tvTitle.text = it.post.title
            binding.tvBody.text = it.post.body
        }

        setUpTransitions()
    }

    private fun setUpTransitions() {

        val transitions = TransitionSet()

        val transitionSetIvArcMove = TransitionInflater.from(this).
        inflateTransition(R.transition.activity2_detail_transition)

        transitionSetIvArcMove.interpolator = AccelerateDecelerateInterpolator()

        // Slide Transition with delay for texts
        val slide = createSlideTransition()
        transitions.addTransition(slide)

        // Fade Transition
        val fade: Transition = createFadeTransition()
        transitions.addTransition(fade)

        // Set Window transition for Shared transitions
        window.enterTransition = transitions

        // 🔥 Should be sharedElementEnterTransition NOT enterTransition
        window.sharedElementEnterTransition = transitionSetIvArcMove

        // Start postponed transition
        startPostponedEnterTransition()

    }

    private fun createSlideTransition() : Transition {
        val slide = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                this@Activity1_2Details,
                android.R.interpolator.linear_out_slow_in
            )
            startDelay = 200
            duration = 500
            addTarget(binding.tvTitle)
            addTarget(binding.tvBody)
        }

        // Add color change to Title after Slide Transition is complete
        slide.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) = Unit

            override fun onTransitionEnd(transition: Transition?) {

                val colorFrom = binding.tvTitle.currentTextColor
                val colorTo = Color.parseColor("#FF8F00")

                val colorAnimation: ValueAnimator =
                    ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.addUpdateListener { animator ->
                    binding.tvTitle.setTextColor(animator.animatedValue as Int)
                }

                colorAnimation.duration = 200

                colorAnimation.start()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit

        })

        return slide
    }

    private fun createFadeTransition() : Transition {
        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(androidx.appcompat.R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        return fade
    }

}