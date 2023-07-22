package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.AddItemRequest
import com.fidilaundry.app.model.request.DeleteItemRequest
import com.fidilaundry.app.model.request.UpdateItemRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.Constant

interface MasterRepository {
    suspend fun getService(): UseCaseResult<ServiceListResponse>
    suspend fun getCategory(): UseCaseResult<CategoryListResponse>
    suspend fun getItem(): UseCaseResult<ItemListResponse>
    suspend fun addItem(req: AddItemRequest): UseCaseResult<BaseObjResponse>
    suspend fun updateItem(req: UpdateItemRequest): UseCaseResult<BaseObjResponse>
    suspend fun deleteItem(req: DeleteItemRequest): UseCaseResult<BaseObjResponse>
}

class MasterRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    MasterRepository {

    override suspend fun getService(): UseCaseResult<ServiceListResponse> {
        return try {
            val result = api.getServiceList(paperPrefs.getToken())
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun getCategory(): UseCaseResult<CategoryListResponse> {
        return try {
            val result = api.getCategoryList(paperPrefs.getToken())
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun getItem(): UseCaseResult<ItemListResponse> {
        return try {
            val result = api.getItemList(paperPrefs.getToken())
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun addItem(req: AddItemRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val contentType = "application/json"
            val result = api.addItem(paperPrefs.getToken(), contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun updateItem(req: UpdateItemRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val contentType = "application/json"
            val result = api.updateItem(paperPrefs.getToken(), contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun deleteItem(req: DeleteItemRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val contentType = "application/json"
            val result = api.deleteItem(paperPrefs.getToken(), contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }
}