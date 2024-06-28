window.addEventListener('load', function(){
        const form = document.forms[0];
        const fechaTurnoDto = document.querySelector("#fechaTurno");
        const pacienteID = document.querySelector("#pacienteId");
        const odontologoID = document.querySelector("#odontologoId");

        form.addEventListener('submit', function(e) {
            e.preventDefault();

            const payload = {
                fecha: fechaTurnoDto.value,
                paciente: {
                    id: pacienteID.value
                },
                odontologo: {
                    id: odontologoID.value
                }
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
            fetch("/turnos/registrar", settings)
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
                    <strong>Exito! Turno agregado</strong>
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