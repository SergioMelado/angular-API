--------------------------------------------------------
-- Archivo creado  - martes-junio-04-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table PRODUCTS
--------------------------------------------------------

Insert into PRODUCTS (PRODUCT_ID,DESCRIPTION,NAME,PRICE) values ('2','A great phone with one of the best cameras','Phone Mini','699');
Insert into PRODUCTS (PRODUCT_ID,DESCRIPTION,NAME,PRICE) values ('3',null,'Phone Standard','299');
Insert into PRODUCTS (PRODUCT_ID,DESCRIPTION,NAME,PRICE) values ('1','A large phone with one of the best screens','Phone XL','799');

--------------------------------------------------------
--  Constraints for Table PRODUCTS
--------------------------------------------------------

  ALTER TABLE "PRODUCTS" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCTS" MODIFY ("NAME" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCTS" MODIFY ("PRICE" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCTS" ADD PRIMARY KEY ("PRODUCT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
