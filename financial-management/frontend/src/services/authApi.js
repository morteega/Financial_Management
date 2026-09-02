const BASE_URL = '/api/users';

export async function registerUser(email,password){
    const params= new URLSearchParams({email, password});

    const response= await fetch(`${BASE_URL}?${params.toString()}`, {
        method:"POST",
    });

    if(!response.ok){
        throw new Error("No se pudo crear el usuario");
    }
    return response.json();
}

export async function loginUser(email, password){
    const params= new URLSearchParams({email, password});

    const response= await fetch(`${BASE_URL}?${params.toString()}`,{
        method:"GET",
    });

    if(!response.ok)
        throw new Error("Email or password incorrect");

    return response.json();
}