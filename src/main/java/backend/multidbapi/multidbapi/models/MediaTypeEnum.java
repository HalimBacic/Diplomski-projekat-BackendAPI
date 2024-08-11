package backend.multidbapi.multidbapi.models;

public enum MediaTypeEnum {
    IMAGEJPEG("image/jpeg"),
    IMAGEPNG("image/png"),
    IMAGEGIF("image/gif"),
    IMAGESVGXML("image/svg+xml"),
    VIDEOMP4("video/mp4"),
    VIDEOMPEG("video/mpeg"),
    AUDIOMPEG("audio/mpeg"),
    AUDIOWAV("audio/wav");

    private final String mimeType;

    MediaTypeEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static String findExtension(MediaTypeEnum type) {
        switch (type) {
            case IMAGEJPEG:
                return ".jpeg";
            case IMAGEGIF:
                return ".gif";
            case IMAGEPNG:
                return ".png";
            case IMAGESVGXML:
                return ".svg";
            case VIDEOMP4:
                return ".mp4";
            case VIDEOMPEG:
                return ".mpeg";
            default:
                throw new IllegalArgumentException("Unknown media type: " + type);
        }
    }
}