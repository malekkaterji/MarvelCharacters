package com.malekkaterji.marvelcharacters.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    characterRemoteDataSource: CharacterRemoteDataSource
){
    val remote = characterRemoteDataSource
}