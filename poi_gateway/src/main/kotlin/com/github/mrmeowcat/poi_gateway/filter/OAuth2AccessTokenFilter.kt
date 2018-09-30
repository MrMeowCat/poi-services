package com.github.mrmeowcat.poi_gateway.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * Filter for OAuth2 access token requests.
 */
@Component
class OAuth2AccessTokenFilter : ZuulFilter() {

    /**
     * Keys for OAuth2 request params.
     */
    private companion object {
        const val CLIENT_ID_KEY = "client_id"
        const val CLIENT_SECRET_KEY = "client_secret"
        const val GRANT_TYPE_KEY = "grant_type"
        const val SCOPE_KEY = "scope"
    }

    /**
     * Access token URL.
     */
    @Value("\${oauth2.client.accessTokenUrl}")
    private lateinit var accessTokenUrl: String
    /**
     * OAuth2 client ID.
     */
    @Value("\${oauth2.client.id}")
    private lateinit var clientId: String
    /**
     * OAuth2 client secret.
     */
    @Value("\${oauth2.client.secret}")
    private lateinit var clientSecret: String
    /**
     * OAuth2 grant types.
     */
    @Value("\${oauth2.client.grantTypes}")
    private lateinit var grantTypes: Array<String>
    /**
     * OAuth2 scopes.
     */
    @Value("\${oauth2.client.scopes}")
    private lateinit var scopes: Array<String>

    /**
     * Sets additional OAuth2 specific params for matching requests.
     */
    override fun run(): Any? {
        val context: RequestContext = RequestContext.getCurrentContext()
        val request: HttpServletRequest = context.request
        if (request.servletPath.contains(accessTokenUrl) && HttpMethod.POST.matches(request.method)) {
            context.requestQueryParams[CLIENT_ID_KEY] = listOf(clientId)
            context.requestQueryParams[CLIENT_SECRET_KEY] = listOf(clientSecret)
            context.requestQueryParams[GRANT_TYPE_KEY] = grantTypes.toList()
            context.requestQueryParams[SCOPE_KEY] = scopes.toList()
        }
        return null
    }

    /**
     * Allows filtering.
     */
    override fun shouldFilter(): Boolean {
        return true
    }

    /**
     * Returns filter type.
     */
    override fun filterType(): String {
        return ROUTE_TYPE
    }

    /**
     * Returns filter order.
     */
    override fun filterOrder(): Int {
        return SIMPLE_HOST_ROUTING_FILTER_ORDER - 1
    }
}
