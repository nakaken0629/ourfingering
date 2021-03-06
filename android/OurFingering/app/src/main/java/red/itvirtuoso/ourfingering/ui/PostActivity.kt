package red.itvirtuoso.ourfingering.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post.*
import red.itvirtuoso.ourfingering.PhotoUtils
import red.itvirtuoso.ourfingering.R

class PostActivity : AppCompatActivity() {
    companion object {
        private const val KEY_PHOTO_PATH = "PHOTO_PATH"
        private const val BUNDLE_PHOTO_PATH = "PHOTO_PATH"
        private const val BUNDLE_INSTRUMENT = "INSTRUMENT"
        private const val BUNDLE_COMPOSER = "COMPOSER"
        private const val BUNDLE_TITLE = "TITLE"

        fun createIntent(context: Context, photoPath: String): Intent {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(KEY_PHOTO_PATH, photoPath)
            return intent
        }
    }

    private lateinit var photoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener { finish() }

        savedInstanceState?.run {
            photoPath = getString(BUNDLE_PHOTO_PATH)
            instrumentEdit.setText(getString(BUNDLE_INSTRUMENT))
            composerEdit.setText(getString(BUNDLE_COMPOSER))
            titleEdit.setText(getString(BUNDLE_TITLE))
        } ?: run {
            photoPath = intent.getStringExtra(KEY_PHOTO_PATH)
        }
        fingeringImage.viewTreeObserver.addOnGlobalLayoutListener {
            val bitmap = PhotoUtils.createBitmap(fingeringImage.width, fingeringImage.height, photoPath)
            fingeringImage.setImageBitmap(bitmap)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putString(BUNDLE_PHOTO_PATH, photoPath)
            putString(BUNDLE_INSTRUMENT, instrumentEdit.text.toString())
            putString(BUNDLE_COMPOSER, composerEdit.text.toString())
            putString(BUNDLE_TITLE, titleEdit.text.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                setResult(Activity.RESULT_OK)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
