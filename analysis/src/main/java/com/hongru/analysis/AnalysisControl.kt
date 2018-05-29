package com.hongru.analysis

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

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
object AnalysisControl {

    private lateinit var appkey: String
    private var channel: String = "defalut"
    private var deviceType: Int = UMConfigure.DEVICE_TYPE_PHONE
    private var pushSecret: String? = null
    private var isCatchErrorEnable: Boolean = false
    private var isDebugMode: Boolean = true


    fun applyApplication(app: Application): AnalysisControl {
        ApplicationHolder.setAgent(object : ApplicationAgent {
            override fun applyApplication(): Application {
                return app
            }

        })
        return this
    }

    fun applyAppKey(appkey: String): AnalysisControl {
        this.appkey = appkey
        return this
    }

    fun applyChannel(channel: String): AnalysisControl {
        this.channel = channel
        return this
    }

    fun applyDeviceType(deviceType: Int): AnalysisControl {
        this.deviceType = deviceType
        return this
    }

    fun applyPushSecret(pushSecret: String): AnalysisControl {
        this.pushSecret = pushSecret
        return this
    }

    fun applyCatchErrorEnable(isCatchErrorEnable: Boolean): AnalysisControl {
        this.isCatchErrorEnable = isCatchErrorEnable
        return this
    }

    fun applyBuildingType(isDebugMode: Boolean): AnalysisControl {
        this.isDebugMode = isDebugMode
        return this
    }

    fun init() {
        /*
        注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
        */
        UMConfigure.init(ApplicationHolder.context(), appkey, channel, deviceType, pushSecret)
        ApplicationHolder.context().registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
                MobclickAgent.onPause(activity)
            }

            override fun onActivityResumed(activity: Activity?) {
                MobclickAgent.onResume(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
            }

        })
        MobclickAgent.setCatchUncaughtExceptions(isCatchErrorEnable)
        UMConfigure.setLogEnabled(isDebugMode)
    }


    /**
     * 账号统计
     */
    fun login(channel: String, userId: String) {
        MobclickAgent.onProfileSignIn(channel, userId)
    }

    fun logout() {
        MobclickAgent.onProfileSignOff()
    }

    /**
     * 包含Activity、Fragment或View的应用
     * 手动页面统计API：一次成对的 onPageStart -> onPageEnd 调用，对应一次手动页面生命周期统计。
     */
    fun onViewStart(tag: String) {
        MobclickAgent.onPageStart(tag)
    }

    fun onViewEnd(tag: String) {
        MobclickAgent.onPageEnd(tag)
    }

    /**
     * 自定义事件统计 计数事件
     */
    fun onCustomEvent(eventId: String, label: String = "default") {
        MobclickAgent.onEvent(ApplicationHolder.context(), eventId, label)
    }

    /**
     * 统计点击行为各属性被触发的次数  计算事件
     */
    fun onCustomCountEvent(eventId: String, map: Map<String, String>, du: Int = -1) {
        MobclickAgent.onEventValue(ApplicationHolder.context(), eventId, map, du)
    }

    /**
     * 错误上报
     */
    fun reportError(error: String) {
        MobclickAgent.reportError(ApplicationHolder.context(), error)
    }

    fun reportError(error: Throwable) {
        MobclickAgent.reportError(ApplicationHolder.context(), error)
    }


}