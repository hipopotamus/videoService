package videoservice.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    GLOBAL_EXCEPTION(400, "잘못된 요청입니다.", "000"),
    BIND_EXCEPTION(400, "잘못된 입력값입니다.", "001"),
    FAIL_AUTHENTICATION(401, "인증에 실패했습니다.", "002"),
    UN_AUTHENTICATION(401, "인증되지 않았습니다.", "003"),
    NOT_FOUND_ACCOUNT(404, "계정을 찾을 수 없습니다.", "004"),
    DUPLICATION_EMAIL(400, "동일한 이메일의 계정이 존재합니다.", "005"),
    FORBIDDEN(403, "잘못된 접근입니다.", "006"),
    NOT_FOUND_BOARD(404, "게시글을 찾을 수 없습니다.", "007"),
    UPLOAD_FAILED(500, "파일을 업로드하는데 실패했습니다.", "008"),
    DUPLICATION_NICKNAME(400, "동일한 닉네임의 계정이 존재합니다.", "009"),
    EMPTY_FILE(400, "파일이 비어있습니다.", "010"),
    ILLEGAL_FILENAME(400, "잘못된 형식의 파일 이름입니다.", "011"),
    NOT_FOUND_VIDEO(404, "영상을 찾을 수 없습니다.", "012"),
    IO_EXCEPTION(400, "연결이 비정상적으로 끊겼습니다.", "013");

    private int status;

    private String message;

    private String code;

    ExceptionCode(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
