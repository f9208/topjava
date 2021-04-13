const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: "profile/meals/filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    render: function (data, type, row) {
                        if (type === "display") {
                            return data.substr(0, 10) + " " + data.substr(11, 5);
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    render: function (data, type, row) {
                        if (type === "display") {
                            return "<a><span class=\"fa fa-pencil\" onclick=updateRow(" + row.id + ")></span></a>"
                        }
                        return data;
                    }
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    render: function (data, type, row) {
                        if (type === "display") {
                            return "<a onclick=\"deleteRow(" + row.id + ")\"><span class=\"fa fa-remove\"></span></a>"
                        }
                        return data;
                    }
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }

        })
    );
});