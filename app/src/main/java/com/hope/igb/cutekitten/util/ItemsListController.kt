package com.hope.igb.cutekitten.util

import com.hope.igb.cutekitten.R

class ItemsListController {


    companion object{
         val itemList = arrayOf(
             R.drawable.image_01, R.drawable.image_02, R.drawable.image_03, R.drawable.image_04, R.drawable.image_05,
             R.drawable.image_06, R.drawable.image_07, R.drawable.image_08, R.drawable.image_09, R.drawable.image_010,
             R.drawable.image_011, R.drawable.image_012, R.drawable.image_013, R.drawable.image_014, R.drawable.image_015,
             R.drawable.image_016, R.drawable.image_017, R.drawable.image_018, R.drawable.image_019, R.drawable.image_020,
             R.drawable.image_021, R.drawable.image_022, R.drawable.image_023, R.drawable.image_024, R.drawable.image_025,
             R.drawable.image_026, R.drawable.image_027, R.drawable.image_028, R.drawable.image_029, R.drawable.image_030,
             R.drawable.image_031, R.drawable.image_032, R.drawable.image_033, R.drawable.image_034, R.drawable.image_035,
             R.drawable.image_036, R.drawable.image_037, R.drawable.image_038, R.drawable.image_039, R.drawable.image_040,
             R.drawable.image_041, R.drawable.image_042, R.drawable.image_043, R.drawable.image_044, R.drawable.image_045,
             R.drawable.image_046, R.drawable.image_047, R.drawable.image_048, R.drawable.image_049, R.drawable.image_050,
             R.drawable.image_051, R.drawable.image_052, R.drawable.image_053, R.drawable.image_054, R.drawable.image_055,
             R.drawable.image_056, R.drawable.image_057, R.drawable.image_058, R.drawable.image_059, R.drawable.image_060,
             R.drawable.image_061, R.drawable.image_062, R.drawable.image_063, R.drawable.image_064, R.drawable.image_065,
             R.drawable.image_066, R.drawable.image_067, R.drawable.image_068, R.drawable.image_069, R.drawable.image_070,
             R.drawable.image_071, R.drawable.image_072, R.drawable.image_073, R.drawable.image_074, R.drawable.image_075,
             R.drawable.image_076, R.drawable.image_077, R.drawable.image_078, R.drawable.image_079, R.drawable.image_080,
             R.drawable.image_081, R.drawable.image_082, R.drawable.image_083, R.drawable.image_084, R.drawable.image_085,
             R.drawable.image_086, R.drawable.image_087)



        fun getItemAt(position: Int) : Int{
            return itemList[position]
        }

    }


}