package com.example.cerrojoguardian

import android.content.Context
import android.content.pm.PackageManager

object AppChecker {
    
    /**
     * Verifica si una app está instalada en el dispositivo
     * @param context Contexto de la aplicación
     * @param packageName El package name de la app a verificar (ej: "com.whatsapp", "com.facebook.katana")
     * @return true si la app está instalada, false si no
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    
    /**
     * Obtiene información de una app instalada (nombre, versión, etc.)
     * @param context Contexto de la aplicación
     * @param packageName El package name de la app
     * @return PackageInfo si está instalada, null si no
     */
    fun getAppInfo(context: Context, packageName: String): android.content.pm.PackageInfo? {
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
    
    /**
     * Verifica si la app "Operación Cerrojo" está instalada
     * Verifica tanto la versión release como debug
     * @param context Contexto de la aplicación
     * @return true si está instalada (en cualquier versión), false si no
     */
    fun isOperacionCerrojoInstalled(context: Context): Boolean {
        val packageNameRelease = "com.hackathon.operacioncerrojo"
        val packageNameDebug = "com.hackathon.operacioncerrojo.debug"
        
        return isAppInstalled(context, packageNameRelease) || 
               isAppInstalled(context, packageNameDebug)
    }
}

