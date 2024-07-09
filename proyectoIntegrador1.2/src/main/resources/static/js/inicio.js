window.addEventListener('load', () => {
  const nombre = document.querySelector("#nombreInicio");
  const url = "/odontologos/1"

  obtenerNombre();

  async function obtenerNombre() {
    const settings = {
      method: 'GET'
    }

    try {
      const response = await fetch(url, settings);
      if (!response.ok) {
        throw response;
      }
      const data = await response.json();
      console.log(data);
      nombre.innerHTML = data.nombre
    } catch (err) {
      console.error(err);
    }
  }
})