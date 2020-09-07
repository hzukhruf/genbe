var formBiodata = {
    saveForm: function () {
        // var dataResult = [];
        // var dataResult = {
        //     nik: $('input[name='nik']').val(),
        //     nama: $('input[name='nama']').val(),
        //     alamat: $('input[name='alamat']').val(),
        //     hp: $('input[name='noHp']').val(),
        //     tanggalLahir: $('input[name='tanggalLahir']').val(),
        //     tempatLahir: $('input[name='tempatLahir']').val(),
        //     $.each($('.multi-fields'), function () {
        //         $.each(this, function (name, value) {
        //             console.log(`${name} = ${value}`);
        //         });
        //     });
            var dataResult = getJsonForm($("#form").serializeArray(), true);
        }
        $.ajax({
            url: '/dataPerson',
            method: 'post',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(dataResult),
            success: function (res, status, xhr) {
                if (xhr.status == 200 || xhr.status == 201) {
                    $('#submit').click(function () {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 3000,
                            timerProgressBar: true,
                            onOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })
                        Toast.fire({
                            icon: 'success',
                            title: 'Submitted successfully'
                        })
                    });
                } else {

                }
            },
            erorr: function (err) {
                console.log(err);
            }
        });
    }
}


$('#reset').click(function () {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
    Toast.fire({
        icon: 'success',
        title: 'All data reset successfully'
    })
});
