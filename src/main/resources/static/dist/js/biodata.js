var tableBiodata = {
    create: function () {
        // jika table tersebut datatable, maka clear and dostroy
        if ($.fn.DataTable.isDataTable('#tableBiodata')) {
            //table yg sudah dibentuk menjadi datatable harus d rebuild lagi untuk di instantiasi ulang
            $('#tableBiodata').DataTable().clear();
            $('#tableBiodata').DataTable().destroy();
        }
        $.ajax({
            url: '/dataPerson/getData',
            method: 'get',
            contentType: 'application/json',
            success: function (res, status, xhr) {
                if (xhr.status == 200 || xhr.status == 201) {
                    console.log(res);
                    $('#tableBiodata').DataTable({
                        data: res,
                        columns: [
                            {
                                title: "NIK",
                                data: "nik"
                            },
                            {
                                title: "Nama",
                                data: "nama"
                            },
                            {
                                title: "Alamat",
                                data: "alamat"
                            },
                            {
                                title: "No Hp",
                                data: "noHp"
                            },
                            {
                                title: "Tanggal Lahir",
                                data: "tanggalLahir"
                            },
                            {
                                title: "Tempat Lahir",
                                data: "tempatLahir"
                            },
                            {
                                title: "Action",
                                data: null,
                                render: function (data, type, row) {
                                    return "<button class='btn btn-olive btn-sm' onclick=formBiodata.setEditData('" + data.idBio + "')>Edit</button>"
                                }
                            }
                        ]
                    });

                } else {

                }
            },
            error: function (err) {
                console.log(err);
            }
        });


    }
};
var formBiodata = {
    resetForm: function () {
        $('#form-biodata')[0].reset();
    },
    saveForm: function () {
       // if ($('#form-biodata').parsley().validate()) {
            var dataResult = getJsonForm($("#form-biodata").serializeArray(), true);
            console.log(dataResult)
            $.ajax({
                url: '/dataPerson',
                method: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(dataResult),
                success: function (result) {
                    console.log(result.status)
                    $('#modal-biodata').modal('hide')
                    if (result.status == true) {
                        tableBiodata.create();
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
       // }
    },
    setEditData: function (idBio) {
        formBiodata.resetForm();
        $.ajax({
            url: '/dataPerson/data/' + idBio,
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (res, status, xhr) {
                if (xhr.status == 200 || xhr.status == 201) {
                    $('#form-biodata').fromJSON(JSON.stringify(res));
                    $('#modal-biodata').modal('show')
                } else {
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
};
var tableBiodataByNik = {
    create: function (nik) {
        // jika table tersebut datatable, maka clear and dostroy
        if ($.fn.DataTable.isDataTable('#tableBiodataByNik')) {
            //table yg sudah dibentuk menjadi datatable harus d rebuild lagi untuk di instantiasi ulang
            $('#tableBiodataByNik').DataTable().clear();
            $('#tableBiodataByNik').DataTable().destroy();
        }

        $.ajax({
            url: '/dataPerson/' + nik,
            method: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (result, status, xhr) {
                console.log(result);
                console.log(xhr.status);
                console.log(result[0].status);
                if (result[0].status == true) {
                    $('#tableBiodataByNik').DataTable({
                        "paging": false,
                        "ordering": false,
                        "info": false,
                        "searching": false,
                        "lengthChange": false,
                        "autoWidth": false,
                        data: [result[0].data],
                        columns: [
                            {
                                title: "NIK",
                                data: "nik"
                            },
                            {
                                title: "Nama",
                                data: "nama"
                            },
                            {
                                title: "Alamat",
                                data: "alamat"
                            },
                            {
                                title: "No Hp",
                                data: "noHp"
                            },
                            {
                                title: "Tanggal Lahir",
                                data: "tanggalLahir"
                            },
                            {
                                title: "Tempat Lahir",
                                data: "tempatLahir"
                            },
                            {
                                title: "Umur",
                                data: "umur"
                            },
                            {
                                title: "Pendidikan Terakhir",
                                data: "pendidikanTerakhir"
                            },
                        ]
                    });
                    Swal.fire(
                        'Sukses!',
                        result[0].message,
                        'success'
                    )
                } else {
                    $('#tableBiodataByNik').empty();
                    Swal.fire(
                        'Gagal!',
                        result[0].message,
                        'error'
                    )
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
};


