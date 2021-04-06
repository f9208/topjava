let form;

const userAjaxUrl = "admin/users/";
const mealAjaxUrl = "meals/";

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;

    form = $('#detailsForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow($(this).closest('tr').attr("id"));
        }
    });

    $(".deleteMeal").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow($(this).closest('tr').attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function add() {
    form.find(":input").val("");
    $("#editMealRow").modal();
    $("#editRow").modal();
}

function updateTable(link) {
    $.get(link, function (data) {
        ctx.datatableApi.clear().rows
            .add(data).draw();
    });
}

function save() {
    const form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + 'create',
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable(ctx.ajaxUrl + 'all');
        successNoty("Saved");
    });
}

function deleteRow(id) {
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable(ctx.ajaxUrl + 'all');
        successNoty("Deleted");
    });
}