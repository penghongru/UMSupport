package com.hongru.siample

import android.app.Application
import com.hongru.analysis.AnalysisControl

//<pre>
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
//</pre>


/**
 *@author 彭鸿儒
 * @date 2018/5/30
 * 邮箱:peng_hongru@163.com
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        AnalysisControl.applyApplication(this)
                .applyAppKey(BuildConfig.UM_APPKEY)
                .applyChannel("default")
                .applyBuildingType(true)
                .applyCatchErrorEnable(true)
                .init()
    }

}