plugins {
    `kotlin-dsl`
}
//Add Repository to solve this issue
// Could not resolve all files for configuration ':kotlinCompilerPluginClasspath'.
//> Could not find org.jetbrains.kotlin:kotlin-sam-with-receiver:1.3.31.
//  Required by:
//      project :
repositories{
    jcenter()
}