package com.ackincolor.tpconcurrence.entities;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

/**
 * un verrou
 */
@ApiModel(description = "un verrou")

public class Lock   {
  @JsonProperty("owner")
  private String owner = null;

  @CreatedDate
  @JsonProperty("created")
  private Date created = null;

  @Id
  @JsonProperty("LockId")
  private String lockId;

  public Lock owner(String owner) {
    this.owner = owner;
    return this;
  }

  /**
   * utilisateur propriétaire du verrou
   * @return owner
  **/
  @ApiModelProperty(value = "utilisateur propriétaire du verrou")


  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Lock created(Date created) {
    this.created = created;
    return this;
  }

  /**
   * date de la pose du verrou
   * @return created
  **/
  @ApiModelProperty(value = "date de la pose du verrou")


  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Lock lock = (Lock) o;
    return Objects.equals(this.owner, lock.owner) &&
            Objects.equals(this.created, lock.created);
  }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }
}

