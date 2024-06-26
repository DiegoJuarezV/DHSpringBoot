window.addEventListener('load', function(){
        const form = document.forms[0];
        const nombrePaciente = document.querySelector("#nombre");
        const apellidoPaciente = document.querySelector("#apellido");
        const cedulaPaciente = document.querySelector("#cedula");
        const fechaIngresoPac = document.querySelector("#fechaIngreso");
        const callePaciente = document.querySelector("#calle");
        const numeroDomicilio = document.querySelector("#numero");
        const localidadPaciente = document.querySelector("#localidad");
        const provinciaPaciente = document.querySelector("#provincia");
        const emailPaciente = document.querySelector("#email");

        form.addEventListener('submit', function(e) {
            e.preventDefault();

            const payload = {
                nombre: nombrePaciente.value,
                apellido: apellidoPaciente.value,
                cedula: cedulaPaciente.value,
                fechaIngreso: fechaIngresoPac.value,
                calle: callePaciente.value,
                numero: numeroDomicilio.value,
                localidad: localidadPaciente.value,
                provincia: provinciaPaciente.value,
                email: emailPaciente.value
            };

            const settings = {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify(payload)
            };

            realizarRegistro(settings);
            form.reset();
        })

        function realizarRegistro(settings) {
            fetch("/pacientes/registrar", settings)
                .then(async response => {
                    if (!response.ok) {
                        throw new Error("Error en la respuesta del servidor")
                    }
                    const data = await response.json();
                    renderizarMsjExito();
                    })
                    .catch (error => {
                        renderizarMsjError();
                })
        }

        function renderizarMsjExito() {
            const divRespuesta = document.querySelector("#response");

            divRespuesta.style.display = "block";
            divRespuesta.innerHTML = `
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Exito! Paciente agregado</strong>
                </div>
            `
        }

        function renderizarMsjError() {
            const divRespuesta = document.querySelector("#response");

            divRespuesta.style.display = "block";
            divRespuesta.innerHTML = `
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong> Error intente nuevamente</strong>
                </div>
            `
        }
    })