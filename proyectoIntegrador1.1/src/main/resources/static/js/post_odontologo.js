window.addEventListener('load', function(){
        const form = document.forms[0];
        const matriculaOdon = document.querySelector("#matricula");
        const nombreOdon = document.querySelector("#nombre");
        const apellidoOdon = document.querySelector("#apellido");

        form.addEventListener('submit', function(e) {
            e.preventDefault();

            const payload = {
                numeroMatricula: matriculaOdon.value,
                nombre: nombreOdon.value,
                apellido: apellidoOdon.value
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
            fetch("/odontologos/registrar", settings)
                .then(async response => {
                    if (response.ok != true) {
                        return Promise.reject(response)
                    }
                    try {
                        const data = await response.json();
                        renderizarMsjExito();
                    } catch (error) {
                        renderizarMsjError();
                    }
                })
        }

        function renderizarMsjExito() {
            const divRespuesta = document.querySelector("#response");

            divRespuesta.style.display = "block";
            divRespuesta.innerHTML = `
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Exito!</strong> Pelicula agregada
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