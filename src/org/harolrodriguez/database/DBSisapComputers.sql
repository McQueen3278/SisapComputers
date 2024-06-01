Drop database if exists DBSisapComputers;
Create database DBSisapComputers;

use DBSisapComputers;


-- Se crea la entidad Computadoras
create table Computadoras(
	computadoraID int not null,
    modelo varchar(100),
    marca varchar(50),
    descripcion varchar(100),
    departamentoAsignado varchar(50),
    primary key PK_computadoraID(computadoraID)

);


-- Se crea la entidad Servidores
create table Servidores(
	servidorID int not null,
    tipoServidor varchar(50),
    descripcion varchar(50),
    marca varchar(50),
    primary key PK_servidorID(servidorID)
);

-- -------------------------------------Agregar Computadora --------------------------------------------------------------------
Delimiter $$
	create procedure sp_agregarComputadora(
    in p_computadoraID int,
    in p_modelo varchar(100),
    in p_marca varchar(50),
    in p_descripcion varchar(100),
    in p_departamentoAsignado varchar(50)
)
BEGIN
    INSERT INTO Computadoras (computadoraID, modelo, marca, descripcion, departamentoAsignado)
    VALUES (p_computadoraID, p_modelo, p_marca, p_descripcion, p_departamentoAsignado);
END$$

Delimiter ;
call sp_agregarComputadora(1, 'MacBook Pro 2020', 'Apple', 'Laptop para diseño gráfico', 'Departamento de Diseño');
call sp_agregarComputadora(2, 'Latitude 7410', 'Dell', 'Laptop de alto rendimiento para negocios', 'Departamento de Ventas');
call sp_agregarComputadora(3, 'ThinkPad X1 Carbon', 'Lenovo', 'Laptop ligera y duradera', 'Departamento de IT');

-- -------------------------------------------Lisar Computadoras -----------------------------------------------------------------
Delimiter $$
	create procedure sp_ListarComputadoras()
		Begin
			Select
				C.computadoraID,
				C.modelo,
				C.marca,
				C.descripcion,
				C.departamentoAsignado
				from Computadoras C;
	end$$
Delimiter ;
call sp_ListarComputadoras();

-- ---------------------------Editar computadora ---------------------------------------------------------------------------------
Delimiter $$

	create procedure sp_actualizarComputadora(
		in p_computadoraID int,
		in p_modelo varchar(100),
		in p_marca varchar(50),
		in p_descripcion varchar(100),
		in p_departamentoAsignado varchar(50)
	)
	BEGIN
		Update Computadoras
		set modelo = p_modelo,
			marca = p_marca,
			descripcion = p_descripcion,
			departamentoAsignado = p_departamentoAsignado
		where computadoraID = p_computadoraID;
	END$$

Delimiter ;
call sp_actualizarComputadora(1, 'MacBook Pro 2021', 'Apple', 'Laptop actualizada para diseño gráfico', 'Departamento de Diseño');
call sp_actualizarComputadora(2, 'Latitude 7420', 'Dell', 'Laptop actualizada para negocios', 'Departamento de Ventas');
call sp_actualizarComputadora(3, 'ThinkPad X1 Carbon Gen 9', 'Lenovo', 'Última generación de laptop ligera y duradera', 'Departamento de IT');

--  ---------------------------------------Eliminar Computadora ------------------------------------------------------------------------------
Delimiter $$

	create procedure sp_eliminarComputadora(
		in p_computadoraID int
	)
	begin
		delete from Computadoras
		where computadoraID = p_computadoraID;
	end$$

Delimiter ;

call sp_eliminarComputadora(1);
call sp_eliminarComputadora(2);

 -- ------------------------------------- Agregar Servidor --------------------------------------------------------------------------------
 Delimiter $$

	create procedure sp_agregarServidor(
		in p_servidorID int,
		in p_tipoServidor varchar(50),
		in p_descripcion varchar(50),
		in p_marca varchar(50)
	)
	begin
		insert  into Servidores (servidorID, tipoServidor, descripcion, marca)
		VALUES (p_servidorID, p_tipoServidor, p_descripcion, p_marca);
	end$$

Delimiter ;
call sp_agregarServidor(1, 'Web Server', 'Servidor para alojar sitios web', 'Dell');
call sp_agregarServidor(2, 'Database Server', 'Servidor para bases de datos', 'HP');
call sp_agregarServidor(3, 'File Server', 'Servidor para almacenamiento de archivos', 'Lenovo');

-- -------------------------------------Listar Servidores -------------------------------------------------------------------------------+
Delimiter $$
	create procedure sp_listarServidores()
		begin
			select
				S.servidorID,
                S.tipoServidor,
                S.descripcion,
                S.marca
                from Servidores S;
		end$$
Delimiter ;
call sp_listarServidores();
-- ----------------------------------------------Actualizar servidor ----------------------------------------------------------------------
Delimiter $$

	create procedure sp_actualizarServidor(
		in p_servidorID int,
		in p_tipoServidor varchar(50),
		in p_descripcion varchar(50),
		in p_marca varchar(50)
	)
	begin
		update Servidores
		set tipoServidor = p_tipoServidor,
			descripcion = p_descripcion,
			marca = p_marca
		where servidorID = p_servidorID;
	end$$
Delimiter ;
call sp_actualizarServidor(1, 'Web Server', 'Servidor para sitios web actualizado', 'Dell');
call sp_actualizarServidor(2, 'Database Server', 'Servidor para bases de datos actualizado', 'HP');
call sp_actualizarServidor(3, 'File Server', 'Servidor de almacenamiento actualizado', 'Lenovo');
-- -------------------------------- Eliminar Servidor ---------------------------------------------------------------------------------------------

Delimiter $$

	create procedure sp_eliminarServidor(
		IN p_servidorID INT
	)
	begin
		delete from Servidores
		where servidorID = p_servidorID;
	end$$

Delimiter ;
call sp_eliminarServidor(1);
call sp_eliminarServidor(2);


