$(function () {
    makeEditable(
        $("#mealstable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function addMeal() {
    form.find(":input").val("");
    $("#editMealRow").modal();
}

function save() {
    const form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ctx.ajaxMealUrl+'/create',
        data: form.serialize()
    }).done(function () {
        $("#editMealRow").modal("hide");
        updateMealsTable();
        successNoty("Saved");
    });
}

function deleteMeal(id) {
    $.ajax({
        url: ctx.ajaxMealUrl + '/'+id,
        type: "DELETE"
    }).done(function () {
        updateMealsTable();
        successNoty("Deleted");
    });
}

function updateMealsTable() {
    $.get(ctx.ajaxMealUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    })
}
