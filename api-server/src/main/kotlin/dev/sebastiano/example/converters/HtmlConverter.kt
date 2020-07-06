package dev.sebastiano.example.converters

import dev.sebastiano.example.setup.withCharsetUtf8
import io.ktor.application.ApplicationCall
import io.ktor.features.ContentConverter
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.pre
import kotlinx.html.stream.createHTML
import kotlinx.html.title
import ws.hook.models.rest.ApiEmployee
import ws.hook.models.rest.ApiErrorResponse

class HtmlConverter : ContentConverter {

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        TODO("Not supported")
    }

    override suspend fun convertForSend(context: PipelineContext<Any, ApplicationCall>, contentType: ContentType, value: Any): Any? {
        val html = when (value) {
            is ApiEmployee -> renderApiEmployee(value)
            is ApiErrorResponse -> renderApiErrorResponse(value.error)
            else -> throw TODO("Not supported yet")
        }
        return TextContent(html, contentType.withCharsetUtf8(), HttpStatusCode.OK)
    }

    private fun renderApiEmployee(employee: ApiEmployee): String = createHTML().apply {
        html {
            head {
                title("Employee ${employee.id}")
            }
            body {
                h1 { +employee.name }
                if (employee.jobDescription != null) {
                    h2 { +employee.jobDescription!! }
                }
            }
        }
    }.finalize()

    private fun renderApiErrorResponse(error: ApiErrorResponse.Error): String = createHTML().apply {
        html {
            head {
                title("Error!")
            }
            body {
                h1 { +"An error has occurred" }
                h2 { +error.message }

                if (error.stackTrace != null) {
                    br
                    br
                    p { +"Stack trace:" }
                    pre { +error.stackTrace!! }
                }
            }
        }
    }.finalize()
}
