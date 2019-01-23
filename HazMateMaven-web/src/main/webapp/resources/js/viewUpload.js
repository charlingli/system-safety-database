$(document).on("click", ".ui-fileupload input[type=file]", function(event){
    var component = $(this).closest('.ui-fileupload').find('.ui-icon-close').trigger('click');
    PF('fileNameOverlay').show();
});