var tagName;
var tagSelectedId;

function showHiddenButtons (tagId){
    if(tagName!=null){
        hideButtons(tagSelectedId);
    }
    tagName = document.getElementById(tagId).value;
    tagSelectedId = tagId;
    $("#updateBut".concat(tagId)).css("display", "inline");
    $("#cancelBut".concat(tagId)).css("display", "inline");
    $("#deleteBut".concat(tagId)).css("display", "inline");

    $("#editBut".concat(tagId)).css("display", "none");
    $("#".concat(tagId)).prop('readonly', false);
}

function hideButtons (tagId){
    $("#".concat(tagId)).val(tagName);
    $("#updateBut".concat(tagId)).css("display", "none");
    $("#cancelBut".concat(tagId)).css("display", "none");
    $("#deleteBut".concat(tagId)).css("display", "none");
    $("#editBut".concat(tagId)).css("display", "inline");

    $("#".concat(tagId)).prop('readonly', true);
}

function deleteTag(tagId){
	var deleteResult = document.getElementById("deleteResult".concat(tagId));
	deleteResult.value = "yes";
	$( "#form".concat(tagId) ).submit();
}
function updateTag (tagId) {
    $( "#form".concat(tagId) ).submit();
}

function addTag(){
    $( "#addform").submit();
}