package com.example.android_firebase.model

import java.io.Serializable

class Post: Serializable {
    var id: String? = null
    var title: String? = null
    var body: String? = null
    var img: String = ""

    constructor() {}

    constructor(title: String, body: String) {
        this.title = title
        this.body = body
    }
    constructor(id: String, title: String, body: String) {
        this.id = id
        this.title = title
        this.body = body
    }

    override fun toString(): String {
        return "Post { " + "id = " + id + ", title = " + title +
                ", body = " + body + ", img = " + img + " }"
    }
}