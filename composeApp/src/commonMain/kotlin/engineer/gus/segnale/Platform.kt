package engineer.gus.segnale

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
