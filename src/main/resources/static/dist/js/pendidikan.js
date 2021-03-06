var idRow = 0;
var formPendidikan = {    
    create: function () {
        var listPendidikan = getJsonForm($("#form-pendidikan").serializeArray(), true);
        pendidikanForm.push(listPendidikan);
        $('#modal-pendidikan').modal('hide')
        if ($.fn.DataTable.isDataTable('#tablePendidikan')) {
            //table yg sudah dibentuk menjadi datatable harus d rebuild lagi untuk di instantiasi ulang
            $('#tablePendidikan').DataTable().clear();
            $('#tablePendidikan').DataTable().destroy();
        }
        $('#tablePendidikan').DataTable({
            data: pendidikanForm,
            columns: [
                {
                    title: "Jenjang",
                    data: "jenjang"
                },
                {
                    title: "Institusi",
                    data: "institusi"
                },
                {
                    title: "Tahun Masuk",
                    data: "masuk"
                },
                {
                    title: "Tahun Lulus",
                    data: "lulus"
                },
                {
                    title: "Action",
                    data: null,
                    render: function (data, type, full, meta) {
                        //console.log(meta.row)
                        return "<button class='btn btn-olive btn-sm' onclick=editPendidikan.setEditData('" + meta.row + "')>Edit</button>"
                    }
                }
            ]
        });
        // console.log(pendidikanForm);
    },
    resetForm: function () {
        $('#form-pendidikan')[0].reset();
    },
    saveForm: function (idPerson, pendidikanForm) {
        $.ajax({
            url: '/dataPerson/insertPendidikan?idPerson=' + idPerson,
            method: 'post',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(pendidikanForm),
            success: function (result) {
                if (result.status == true) {
                    Swal.fire(
                        'Sukses!',
                        result.message,
                        'success'
                    )
                } else {
                    Swal.fire(
                        'Gagal!',
                        result.message,
                        'error'
                    )
                }
            },
            error: function (err) {
                console.log(err);
            }
        });

        $('#tablePendidikan').DataTable().clear();
        $('#tablePendidikan').DataTable().destroy();
        $('#tablePendidikan').empty();
    },

    saveEditData: function () {
        var editData = getJsonForm($("#form-pendidikan-edit").serializeArray(), true);
        pendidikanForm[idRow] = editData;
        $('#modal-pendidikan-edit').modal('hide')
        if ($.fn.DataTable.isDataTable('#tablePendidikan')) {
            //table yg sudah dibentuk menjadi datatable harus d rebuild lagi untuk di instantiasi ulang
            $('#tablePendidikan').DataTable().clear();
            $('#tablePendidikan').DataTable().destroy();
        }
        $('#tablePendidikan').DataTable({
            data: pendidikanForm,
            columns: [
                {
                    title: "Jenjang",
                    data: "jenjang"
                },
                {
                    title: "Institusi",
                    data: "institusi"
                },
                {
                    title: "Tahun Masuk",
                    data: "masuk"
                },
                {
                    title: "Tahun Lulus",
                    data: "lulus"
                },
                {
                    title: "Action",
                    data: null,
                    render: function (data, type, full, meta) {
                        return "<button class='btn btn-olive btn-sm' onclick=editPendidikan.setEditData('" + meta.row + "')>Edit</button>"
                    }
                }
            ]
        });
    }
};
var editPendidikan = {
    setEditData: function (idEdit) {
        $('#form-pendidikan-edit').fromJSON(JSON.stringify(pendidikanForm[idEdit]));
        $('#modal-pendidikan-edit').modal('show');
        idRow = idEdit;
    }
}