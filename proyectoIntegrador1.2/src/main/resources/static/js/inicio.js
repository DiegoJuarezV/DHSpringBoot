window.addEventListener('load', () => {
  const nombreUser = document.querySelector("#nombreInicio");
  const userId = localStorage.getItem('userId');
  const apiUrl = `/usuarios/${userId}`;

  async function obtenerRegistros() {
    const settings = {
      method: 'GET'
    }

    try {
      const response = await fetch(apiUrl, settings);
      if (!response.ok) {
        throw response;
      }
      const data = await response.json();
      nombreUser.innerHTML = data.nombre
    } catch (err) {
      console.error(err);
    }
  }
})

