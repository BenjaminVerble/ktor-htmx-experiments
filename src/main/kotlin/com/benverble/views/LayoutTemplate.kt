package com.benverble.views

import io.ktor.server.html.*
import kotlinx.html.*

class LayoutTemplate: Template<HTML> {
    val pageHead = TemplatePlaceholder<ContentTemplate>()
    val content = TemplatePlaceholder<ContentTemplate>()
    override fun HTML.apply() {
        head {
            link(rel = "stylesheet", href = "/static/css/simple.css", type = "text/css")
            link(rel = "stylesheet", href = "/styles.css", type = "text/css")
        }
        body {
            insert(ContentTemplate(), content)

            footer {
                script(src = "/static/js/script.js") {}
                script(src = "/static/js/htmx.min.js") {}
            }
        }
    }
}

class ContentTemplate: Template<FlowContent> {
    val articleTitle = Placeholder<FlowContent>()
    //    val articleText = Placeholder<FlowContent>()
    override fun FlowContent.apply() {
        article {
            h1("my-h1") {
                insert(articleTitle)
            }
            // todo: native css transitions for dialog
            dialog {
                id = "test-dialog"
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
            button {
                attributes["hx-target"] = "#test-dialog"
                attributes["hx-swap"] = "outerHTML"
                attributes["hx-get"] = "/test-dialog"
                +"show the dialog"
            }
        }
        div {
            a {
                attributes["href"] = "/messages"
                attributes["hx-get"] = "/messages"
                attributes["hx-swap"] = "outerHTML"
                attributes["hx-target"] = "closest div"
                +"get messages"
            }
        }
    }
}
