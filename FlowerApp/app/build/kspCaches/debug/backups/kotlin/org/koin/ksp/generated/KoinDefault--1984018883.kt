package org.koin.ksp.generated

import org.koin.core.module.Module
import org.koin.dsl.*
import org.koin.core.module.dsl.viewModel
import org.koin.meta.annotations.ExternalDefinition
import org.koin.core.definition.KoinDefinition
@ExternalDefinition("com.example.flowerapp.ui.camera.capturepicture")
public fun Module.defineComExampleFlowerappUiCameraCapturepictureSavePhoto() : KoinDefinition<*> = factory() { _ -> com.example.flowerapp.ui.camera.capturepicture.SavePhoto(context=get()) } 
@ExternalDefinition("com.example.flowerapp.ui.camera.capturepicture")
public fun Module.defineComExampleFlowerappUiCameraCapturepictureCameraViewModel() : KoinDefinition<*> = viewModel() { _ -> com.example.flowerapp.ui.camera.capturepicture.CameraViewModel(savePhoto=get()) } 

public val _defaultModule : Module get() = module {
	defineComExampleFlowerappUiCameraCapturepictureSavePhoto()
	defineComExampleFlowerappUiCameraCapturepictureCameraViewModel()
}
public val defaultModule : org.koin.core.module.Module get() = _defaultModule
public fun org.koin.core.KoinApplication.defaultModule(): org.koin.core.KoinApplication = modules(defaultModule)
