import axios from "axios"




let url_prefix = "http://localhost:8080"


function upload(form, callBack) {
    url = url_prefix + "/upload"
    axios({
        "url": "http://aierx.icu:8080/upload",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    }).then(callBack)
}


