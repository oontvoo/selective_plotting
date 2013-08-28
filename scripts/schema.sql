-- timesteps table

CREATE TABLE timesteps (time timestamp primary key not null);

-- concentrations of each ions

CREATE TABLE concentrations (ion_name varchar(16) primary key not null,
       	     		     time timestamp not null,
			     percentage decimal not null,
			     CONSTRAINT FOREIGN KEY (time) REFERENCES  timesteps(time)
			     		ON DELETE CASCADE
					ON UPDATE CASCADE);

-- conductivity 

CREATE TABLE conductivities (cond_hi decimal not null,
       	     		     cond_low decimal not null,
			     time timestamp PRIMARY KEY not null,
			     CONSTRAINT  FOREIGN KEY (time) REFERENCES timesteps(time)
			     		 ON DELETE CASCADE
					 ON UPDATE CASCADE);

-- We should be able to join the three using timesteps.time
-- TODO: look at WORKBENCH