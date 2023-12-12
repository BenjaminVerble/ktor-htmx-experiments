package com.benverble.plugins

import com.benverble.views.LayoutTemplate
import com.benverble.views.mainLayout
import com.benverble.views.testHtmxPage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML

val messages = listOf("foo", "bar", "baz")

fun Application.configureTemplating() {
    routing {
        // todo: separate routing function
        get("/styles.css") {
            call.respondCss {
                rule("h1.page-title") {
                    color = Color.chocolate
                }
            }
        }

        // todo : in htmx routing function
        get("/test-dialog") {
            call.respondText {
                createHTML().dialog {
                    id = "test-dialog"
                    attributes["open"] = "true"
                    form {
                        attributes["method"] = "dialog"
                        button {
                            +"close"
                        }
                    }
                    p {
                        +"Modal has a backdrop!"
                    }
                }
            }
        }

        // todo : in htmx routing function
        get("/messages") {
            call.respondText {
                createHTML().div {
                    ul {
                        messages.map {
                            li {
                                +it
                            }
                        }
                    }
                }.toString()
            }
        }

        get("/html-css-dsl") {
            call.respondHtmlTemplate(LayoutTemplate()) {
                content {
                    articleTitle {
                        +"Fancy htmx kotlin html dsl test page"
                    }
                }
            }
        }

        get("/simple-template") {
            call.respondHtml() {
                mainLayout("my simple page") {
                    testHtmxPage("this is the title from the param") {
                        h1 {
                            +it
                        }
                    }
                }
            }
        }
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

