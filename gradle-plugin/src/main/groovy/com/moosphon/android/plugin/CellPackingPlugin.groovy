package com.moosphon.android.plugin

import com.android.build.gradle.api.BaseVariant
import com.android.builder.model.SigningConfig
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

/**
 * A multi-channel plugin to pack apk.
 * @author moosphon
 * @date 2021/07/16 10:11
 */
class CellPackingPlugin implements org.gradle.api.Plugin<Project> {

    public static final String sPluginExtensionName = "cellPacking"

    @Override
    void apply(Project project) {

    }

    void applyExtension(Project project) {
        project.extensions.create(sPluginExtensionName, Extension, project)
    }


    void applyTask(Project project) {

    }

    SigningConfig getSigningConfig(BaseVariant variant) {
        return variant.buildType.signingConfig == null ? variant.mergedFlavor.signingConfig : variant.buildType.signingConfig
    }
}
