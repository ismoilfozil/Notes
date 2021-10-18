package uz.ismoil.notes.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.ismoil.notes.domain.*

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindMainUseCase(useCase: MainUseCaseImpl): MainUseCase

    @Binds
    fun bindCreateNoteUseCase(useCaseImpl: CreateNoteUseCaseImpl): CreateNoteUseCase

    @Binds
    fun bindEditNoteUseCase(useCaseImpl: EditNoteUseCaseImpl): EditNoteUseCase

    @Binds
    fun bindArchiveUseCase(useCaseImpl: ArchivedUseCaseImpl):ArchivedUseCase

    @Binds
    fun bindTrashUseCase(useCaseImpl: TrashUseCaseImpl):TrashUseCase
}