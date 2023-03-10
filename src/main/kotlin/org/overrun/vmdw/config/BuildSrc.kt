/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.overrun.vmdw.config

import java.io.*
import java.util.*

/**
 * @author baka4n
 */
object BuildSrc {
    var build: Build? = null
    var openDirection: File? = null
        get() {
            return field
        }
        set(value) {
            field = value
        }

    fun load(fileName: String) {
        build = Build(fileName)
    }


}

/**
 * @author baka4n
 */
class Build(fileName: String) {
    val dir = File(System.getProperty("user.dir"), ".vmdw/buildSrc/${fileName}")

    init {
        if (!dir.exists()) dir.mkdirs()
    }
    val modSettings = File(dir, "modSettings.properties")
    var propertiesTools: PropertiesTools = PropertiesTools(modSettings)
    var getModSettings: GetModSettings
    init {
        propertiesTools.init()
        getModSettings = GetModSettings(propertiesTools)
    }
}
/**
 * @author baka4n
 */
class GetModSettings(var propertiesTools: PropertiesTools) {
    fun setModid(modid: String) {
        propertiesTools.put("modid", modid)
    }

    fun setContributors(contributors: String) {
        propertiesTools.put("contributors", contributors)
    }
    fun setAuthors(vararg authors: String) {
        val sb = StringBuilder()

        authors.forEachIndexed { index, it ->
            run {
                sb.append(it)
                if (authors.size - 1 != index) {
                    sb.append(",")
                }
            }
        }
        propertiesTools.put("authors", sb.toString())
    }
}
/**
 * @author baka4n
 */
class PropertiesTools(private val f: File): Properties() {
    fun init() {
        ifs()
        try {
            load()
        } catch (e: FileNotFoundException) {
            try {
                save("save mod settings.")
            } catch (f: IOException) {
                f.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun ifs() {
        if (!this.exists() && !this.isDirectory()) f.mkdirs()
    }

    fun put(key: String?, value: String?) { this[key] = value }

    fun save(title: String) { store(title) }

    fun load() { this.load(BufferedInputStream(FileInputStream(f))) }

    private fun exists(): Boolean { return f.exists() }

    private fun isDirectory(): Boolean { return f.parentFile.isDirectory }

    fun store(title: String?) {
        this.store(BufferedOutputStream(FileOutputStream(f)), title)
    }
}
