package com.hope.igb.cutekitten.screens.displayimage

import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.*
import android.os.StrictMode.VmPolicy
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gw.swipeback.SwipeBackLayout
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.adhelper.AdBannerHelper
import com.hope.igb.cutekitten.adhelper.AdInterstitialHelper
import com.hope.igb.cutekitten.adhelper.AdmobHelper
import com.hope.igb.cutekitten.screens.displayimage.util.ImageSaver
import com.hope.igb.cutekitten.screens.displayimage.util.WallpaperSetter
import com.hope.igb.cutekitten.screens.slideshow.SlideShowActivity
import com.hope.igb.cutekitten.util.InAppReviewHelper
import com.hope.igb.cutekitten.util.ItemsListController
import com.hope.igb.cutekitten.util.PermissionHandler
import java.io.File

class DisplayActivity : AppCompatActivity(), DisplayViewMvc.DisplayListener,
    PermissionHandler.PermissionGrantedListener, WallpaperSetter.WallpaperSetterListener,
    AdBannerHelper.AdmobBannerListener {


    private var initialPosition = 0
    private lateinit var viewMvc: DisplayViewMvc
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var savedImageFileName: String
    private  var wallpaperSetter : WallpaperSetter? =null
    private lateinit var adBanner: AdBannerHelper
    private lateinit var adInterstitial: AdInterstitialHelper
    private lateinit var  reviewHelper : InAppReviewHelper





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mSwipeBackLayout = SwipeBackLayout(this)
        mSwipeBackLayout.directionMode = SwipeBackLayout.FROM_TOP
        mSwipeBackLayout.maskAlpha = 255
        mSwipeBackLayout.swipeBackFactor = 1.0f


        viewMvc = DisplayViewMvc(layoutInflater)

        mSwipeBackLayout.addView(viewMvc.getRootView())


//        setContentView(viewMvc.getRootView())
        setContentView(mSwipeBackLayout)



        initialPosition = intent.getIntExtra("INITIAL_POSITION", 0)

        permissionHandler = PermissionHandler(this)


        adBanner = AdBannerHelper(AdmobHelper.adBannerBottom, viewMvc.adViewBottom)
        adInterstitial = AdInterstitialHelper(this)



        reviewHelper = InAppReviewHelper(this)


    }



    //!!! the problem was here the way you implement swipe back activity



//    private fun swipeActivityToBack(){
//        val mSwipeBackLayout = SwipeBackLayout(this)
//        mSwipeBackLayout.attachToActivity(this)
//        mSwipeBackLayout.directionMode = SwipeBackLayout.FROM_TOP
//        mSwipeBackLayout.maskAlpha = 255
//        mSwipeBackLayout.swipeBackFactor = 1.0f
//    }


    
    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        viewMvc.fetchImages(initialPosition, itemList())

        adBanner.loadAd()
        adInterstitial.initAd(getString(R.string.interstitial_ad_id1))
        adBanner.registerListener(this)
    }


    override fun onStop() {
        super.onStop()

        viewMvc.unregisterListener(this)
            viewMvc.closeMenu()

        if (wallpaperSetter != null)
        wallpaperSetter?.unregisterListener(this)


        adBanner.unregisterListener(this)
    }



    private fun itemList(): Array<Int>{
        return ItemsListController.itemList
    }





    override fun onSaveClicked() {
        val imageFileName = "kitten_"+System.currentTimeMillis()

        grantPermissionAndSave(imageFileName)

    }



    override fun onShareClicked() {
        grantPermissionAndSave("temp_image")
    }





    private fun grantPermissionAndSave(fileName: String){

        if (permissionHandler.isStoragePermissionGranted()){

            val  imageFile = saveImage(fileName)
            onImageImageSaved(imageFile, fileName, false)

        }else{
            savedImageFileName = fileName
            permissionHandler.registerListener(this)
        }


    }



    private fun saveImage(fileName: String): File{
        val imageSaver = ImageSaver(this, ItemsListController.getItemAt(viewMvc.currentPosition))

        if (!imageSaver.appBaseFolder().exists())
            imageSaver.appBaseFolder().mkdir()


        return imageSaver.saveImage(fileName)

    }


    override fun onPermissionGranted() {
        permissionHandler.unregisterListener(this)
        val imageFile = saveImage(savedImageFileName)
        onImageImageSaved(imageFile, savedImageFileName, true)

    }


    override fun onPermissionNotGranted() {
        permissionHandler.unregisterListener(this)

        Toast.makeText(this, "Please give a storage permission to share or save an image", Toast.LENGTH_LONG).show()
    }



    private fun onImageImageSaved(imageFile: File, fileName: String, isFirstLaunch: Boolean){
        if (fileName != "temp_image"){
            Toast.makeText(this, "The image saved successfully to ${imageFile.path}", Toast.LENGTH_LONG).show()
            adInterstitial.showAd()


            if (isFirstLaunch || isChanceToShowReviewCome())
                reviewHelper.reviewApp()


            refreshGalleryToShowTheSavedImage(imageFile)
        }

        else
            shareFile(imageFile)
    }

    private fun isChanceToShowReviewCome(): Boolean {
        val random = (Math.random() * 100).toInt()

        return random != 0 && random % 3==0

    }


    private fun refreshGalleryToShowTheSavedImage(imageFile: File){
        MediaScannerConnection.scanFile(
            this@DisplayActivity, arrayOf(imageFile.toString()), null,
            null
        )
    }



    private fun shareFile(file: File){
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/jpg"
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        startActivity(Intent.createChooser(share, "Share image"))
    }







    override fun onSlideShowClicked() {
        val intent = Intent(this@DisplayActivity, SlideShowActivity::class.java)
        startActivity(intent)
    }





    override fun onSetWallpaperClicked() {
        wallpaperSetter = WallpaperSetter(this, ItemsListController.getItemAt(viewMvc.currentPosition), "home")
        wallpaperSetter?.registerListener(this)
        wallpaperSetter?.execute()


    }



    override fun onSetLockScreenClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperSetter = WallpaperSetter(this, ItemsListController.getItemAt(viewMvc.currentPosition),  "lock")
            wallpaperSetter?.registerListener(this)
            wallpaperSetter?.execute()




        }else
            Toast.makeText(this, "You need android 7 or higher to set lock screen wallpaper", Toast.LENGTH_LONG).show()
    }


    override fun onStartSetting(type: String) {
        runOnUiThread{Toast.makeText(this,
            "Setting $type screen wallpaper....", Toast.LENGTH_LONG).show() }
    }



    override fun onSuccessFullySet(type: String) {
        if (type == "lock")
            Toast.makeText(this, "Lock screen wallpaper setting successfully", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Home wallpaper setting successfully", Toast.LENGTH_LONG).show()

        wallpaperSetter?.unregisterListener(this)

        adInterstitial.showAd()
    }








    override fun onRefreshClicked() {
        Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show()
        recreate()
    }



    override fun onAdLoadedSuccess(adName: String) {
        if (adName == AdmobHelper.adBannerBottom)
            viewMvc.adViewBottom.visibility = View.VISIBLE
    }

    override fun onAdLoadedFailed(adName: String) {
        if (adName == AdmobHelper.adBannerBottom)
            viewMvc.adViewBottom.visibility = View.GONE
    }


}